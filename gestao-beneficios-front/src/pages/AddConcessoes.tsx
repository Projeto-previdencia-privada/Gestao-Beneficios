import {useEffect, useState} from "react";
import Radio from "../components/Radio/Radio.tsx";
import Input from "../components/Input/Input.tsx";

type dataBeneficio = {
    nome: string | ''
}

type dataConcessao = {
    requisitante: string | ''
    beneficiado: string | ''
}
type concessaoEnvio = {
    requisitante: string | ''
    beneficiado: string | ''
    beneficioNome: string | ''
}

const concessoes: dataConcessao={
    requisitante:'',
    beneficiado:'',
}
const inputError: dataConcessao={
    requisitante:'',
    beneficiado:'',
}
const concessaoEnvios: concessaoEnvio={
    requisitante: '',
    beneficiado: '',
    beneficioNome: '',
}




const host: string | undefined = import.meta.env.VITE_HOST
const port: string | undefined = import.meta.env.VITE_PORT
const host_backend: string | undefined = import.meta.env.VITE_HOST_BACKEND
const port_backend: string | undefined = import.meta.env.VITE_PORT_BACKEND


const AddConcessoesPage = () =>{
    const [beneficios, setBeneficios] = useState<dataBeneficio[]>([])
    const [concessao, setConcessao] = useState<dataConcessao>(concessoes)
    const [search, setSearch] = useState('')
    const [err, setErr] = useState<dataConcessao>(inputError)
    const [beneficioEscolhido, setBeneficioEscolhido] = useState('')
    const [concessaoEnvio, setConcessaoEnvio] = useState<concessaoEnvio>(concessaoEnvios)


    const beneficiosFiltered = beneficios.filter((beneficio)=> beneficio.nome.startsWith(search))

    useEffect(()=>{
        getBeneficios()
    },[])

    async function getBeneficios() {
        await fetch('http://'+host_backend+':'+port_backend+'/api/beneficio', {
            method: "GET",
            headers: {
                'Accept': '*/*',
                'Access-Control-Allow-Origin': 'http://'+host+':'+port+'/'
            },
            mode: "cors",
            cache: "default",
        }).then(response => response.json()).then(data => setBeneficios(data))
    }

    const handleSubmit = () =>{
        setConcessaoEnvio({requisitante: concessao.requisitante, beneficiado: concessao.beneficiado, beneficioNome: beneficioEscolhido})
        console.log(concessaoEnvio)
        fetch('http://'+host_backend+':'+port_backend+'/api/concessao', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': 'http://'+host+':'+port+'/'
            }, mode: "cors", cache: "default", body: JSON.stringify(concessaoEnvio)
        })
    }

    const handleChange=(variable: string, data: string) => {
        validateFields(variable, data)
        setConcessao({...concessao, [variable]: data})
    }

    const validateFields=(variable: string, input: string)=>{
        if(variable === 'requisitante'){
            if(input.length==0){
                setErr({...err, requisitante:'Campo Requisitante Vazio'})
            }
            else {
                setErr({...err, requisitante:''})
            }
        }

        if(variable === 'beneficiado'){
            if(input.length==0){
                setErr({...err,  beneficiado:'Campo Beneficiado Vazio'})
            }
            else {
                setErr({...err, beneficiado:''})
            }
        }

    }
    return(
        <>

            <h1>Concessoes</h1>
            <h3>Solicitar Concessao</h3>

            <Input
                id={"requisitante-input"}
                hasButton={false}
                buttonClass={""}
                label={"Requisitante"}
                placeholder={"CPF do Requisitante"}
                type={"search"}
                onChange={(e) => {
                    handleChange('requisitante', e.target.value)
                }}
                classname={''}
                mask={''}
            />
            {err.requisitante && (<br-message
                feedback
                state={"danger"}
                show-icon="true"
                message={err.requisitante}
            ></br-message>)}

            <Input
                id={"beneficiado-input"}
                hasButton={false}
                buttonClass={""}
                label={"Beneficiado"}
                placeholder={"CPF do Beneficiado"}
                type={"search"}
                onChange={(e) => {
                    handleChange('beneficiado', e.target.value)
                }}
                classname={''}
                mask={''}
            />
            {err.beneficiado && (<br-message
                feedback
                state={"danger"}
                show-icon="true"
                message={err.beneficiado}
            ></br-message>)}

            <br-modal title="" show={true} width="auto">
                <Input
                    id={"nome-input"}
                    hasButton={true}
                    buttonClass={"fas fa-search"}
                    label={"Procurar Beneficio"}
                    placeholder={"Nome do Beneficio"}
                    type={"search"}
                    onChange={(e) => {
                        setSearch(e.target.value)
                    }}
                    classname={''}
                    mask={''}
                />
                <Radio
                    title={"Beneficios"}
                    list={beneficiosFiltered}
                    subText={"Escolha somente um beneficio:"}
                    groupName={"beneficios"}
                    onChange={e => setBeneficioEscolhido(e.target.value)}
                />
            </br-modal>

            <div className="mt-3 d-flex justify-content-md-end">
                <br-button
                    icon="check"
                    label="Solicitar"
                    type="primary"
                    submit={true}
                    density="large"
                    onClick={handleSubmit}
                ></br-button>
            </div>
        </>

    )
}

export default AddConcessoesPage
