import { useState } from 'react'
import Input from "../components/Input/Input.tsx";

type data = {
  nome: string | ''
  tempoMinimo: string | ''
  valorPercentual: number | undefined
}

const host: string= import.meta.env.VITE_HOST
const port: string= import.meta.env.VITE_PORT
const host_backend: string= import.meta.env.VITE_HOST_BACKEND
const port_backend: string= import.meta.env.VITE_PORT_BACKEND

const beneficio: data = {
  nome: '',
  tempoMinimo: '',
  valorPercentual: 0,
}
const FormPage = () =>{

  const [value, setValue] = useState<data>(beneficio)
  const [message, setMessage] = useState({message:'', state: '', show: false})


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


  return(
      <>
          <h1>Beneficios</h1>
          <h3>Cadastrar Beneficios</h3>
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
              placeholder={"Nome do Beneficio"}
              type={"search"}
              onChange={(e)=>{setValue({...value, nome : e.target.value})}}
              classname={''}
           />
              <Input
                  id={"tempo-input"}
                  hasButton={false}
                  buttonClass={""}
                  label={"Tempo Minimo"}
                  placeholder={"Tempo Minimo de Contribuicao"}
                  type={"search"}
                  onChange={(e)=>{setValue({...value, tempoMinimo : e.target.value})}}
                  classname={''}
              />
              <Input
                  id={"valor-input"}
                  hasButton={false}
                  buttonClass={""}
                  label={"Valor"}
                  placeholder={"Valor Percentual do Beneficio"}
                  type={"search"}
                  onChange={(e)=>{setValue({...value, valorPercentual : Number(e.target.value)})}}
                  classname={''}
              />
          <div className="mt-3 d-flex justify-content-md-end">
              <br-button
                  icon="check"
                  label="Cadastrar"
                  type="primary"
                  submit={true}
                  density="large"
                  onClick={handleSubmit}
              ></br-button>
          </div>
          </form>
      </>

  )
}

export default FormPage
