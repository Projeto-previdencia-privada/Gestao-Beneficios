import { useState} from 'react'
import Input from "../components/Input/Input.tsx";

type data = {
  nome: string | ''
  tempoMinimo: string | ''
  valorPercentual: string | ''
}


const host: string= import.meta.env.VITE_HOST
const port: string= import.meta.env.VITE_PORT
const host_backend: string= import.meta.env.VITE_HOST_BACKEND
const port_backend: string= import.meta.env.VITE_PORT_BACKEND

const beneficio: data = {
  nome: '',
  tempoMinimo: '',
  valorPercentual: '',
}
const inputError: data = {
    nome: '',
    tempoMinimo: '',
    valorPercentual: '',
}
const FormPage = () =>{

    const [value, setValue] = useState<data>(beneficio)
    const [message, setMessage] = useState({message:'', state: '', show: false})
    const [desabilitado, setDesabilitado] = useState(false)
    const [err, setErr] = useState<data>(inputError)


    const handleSubmit = () =>{
        beneficio.nome = value.nome
        beneficio.valorPercentual= value.valorPercentual
        beneficio.tempoMinimo = value.tempoMinimo
        fetch('http://'+host_backend+':'+port_backend+'/api/beneficio', {
            method: 'POST',
            headers: {
                'Accept': '*/*',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': 'https://'+host+':'+port+'/'
            },
            body: JSON.stringify(beneficio)
        } ).then(response => manageMessage(response))

    }

    const manageMessage = (response: Response) =>{
        if(response.status === 201){
            setMessage({message:'Beneficio cadastrado com sucesso!', show: true, state: "success"})
        }
        else if(response.status === 400) {
            setMessage({message: 'Beneficio ja cadastrado no sistema', show: true, state: "danger"})
        }
    }

    const handleChange=(variable: string, data: string) => {
        validateFields(variable, data)
        setValue({...value, [variable]: data})
    }

    const validateFields=(variable: string, input: string)=>{
        if(variable === 'nome'){
            if(input.length==0){
                setErr({...err, nome:'Nome Vazio'})
            }
            else {
                setErr({...err, nome:''})
            }
        }

        if(variable === 'tempoMinimo'){
            if(input.length==0){
                setErr({...err, tempoMinimo:'Tempo Vazio'})
            }
            else if(!/^\d+$/.test(input)){
                setErr({...err, tempoMinimo:'Coloque apenas números no campo'})
            }
            else {
                setErr({...err, tempoMinimo:''})
            }
        }

        if(variable === 'valorPercentual'){
            if(input.length==0){
                setErr({...err, valorPercentual:'Valor Vazio'})
            }
            else if(isNaN(parseFloat(input))){
                setErr({...err, valorPercentual:'Coloque apenas números no campo'})
            }
            else {
                setErr({...err, valorPercentual:''})
            }
        }

        if(err.nome || err.valorPercentual || err.tempoMinimo){
            setDesabilitado(true)
        }
        if(!err.nome || !err.valorPercentual || !err.tempoMinimo){
            setDesabilitado(false)
        }

    }


    return (
        <>
            <h1>Benefícios</h1>
            <h3>Cadastrar Benefícios</h3>
            {message.show && (<br-message
                state={message.state}
                closable={"true"}
                show-icon="true"
                message={message.message}
          ></br-message>)}
          <form onSubmit={handleSubmit}>
              <Input
                  id={"nome-input"}
                  hasButton={false}
                  buttonClass={""}
                  label={"Nome"}
                  placeholder={"Nome do Benefício"}
                  type={"search"}
                  onChange={(e)=>{handleChange("nome", e.target.value)}}
                  classname={''}
                  mask={''}
              />
              {err.nome && (<br-message
                  feedback
                  state={"danger"}
                  show-icon="true"
                  message={err.nome}
              ></br-message>)}
              <Input
                  id={"tempo-input"}
                  hasButton={false}
                  buttonClass={""}
                  label={"Tempo Mínimo"}
                  placeholder={"Tempo Minimo de Contribuição"}
                  type={"search"}
                  onChange={(e)=>{handleChange("tempoMinimo", e.target.value)}}
                  classname={''}
                  mask={''}
              />
              {err.tempoMinimo && (<br-message
                  feedback
                  state={"danger"}
                  show-icon="true"
                  message={err.tempoMinimo}
              ></br-message>)}
              <Input
                  id={"valor-input"}
                  hasButton={false}
                  buttonClass={""}
                  label={"Valor"}
                  placeholder={"Valor Percentual do Benefício"}
                  type={"search"}
                  onChange={(e)=>{handleChange("valorPercentual", e.target.value)}}
                  classname={''}
                  mask={''}
              />
              {err.valorPercentual && (<br-message
                  feedback
                  state={"danger"}
                  show-icon="true"
                  message={err.valorPercentual}
              ></br-message>)}
              <div className="mt-3 d-flex justify-content-md-end">
                  {!desabilitado && <br-button
                      icon="check"
                      label="Cadastrar"
                      type="primary"
                      submit={true}
                      density="large"
                      onClick={handleSubmit}
                  ></br-button>}
              </div>
          </form>
      </>

  )
}

export default FormPage
