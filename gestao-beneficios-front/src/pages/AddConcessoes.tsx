import {useEffect, useState} from "react";
import Radio from "../components/Radio/Radio.tsx";
import Input from "../components/Input/Input.tsx";

type data = {
    nome: string | ''
    tempoMinimo: string | ''
    valorPercentual: number | undefined
}

const host: string | undefined = import.meta.env.VITE_HOST
const port: string | undefined = import.meta.env.VITE_PORT
const host_backend: string | undefined = import.meta.env.VITE_HOST_BACKEND
const port_backend: string | undefined = import.meta.env.VITE_PORT_BACKEND


const AddConcessoesPage = () =>{
    const [beneficios, setBeneficios] = useState<data[]>([])
    const [search, setSearch] = useState('')

    const beneficiosFiltered = beneficios.filter((beneficio)=> beneficio.nome.startsWith(search))

    useEffect(()=>{
        getBeneficios()
    },[])

    async function getBeneficios() {
        await fetch('http://'+host_backend+':'+port_backend+'/api/beneficio', {
            method: "GET",
            headers: {
                'Accept': '*/*',
                'Access-Control-Allow-Origin': 'https://'+host+':'+port+'/'
            },
            mode: "cors",
            cache: "default",
        })
            .then(response => response.json()).then(data => setBeneficios(data))
    }

    const handleSubmit = () =>{
    fetch('http://'+host_backend+':'+port_backend+'/addConcessoes', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({})
    })
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
                    setSearch(e.target.value)
                }}
                classname={''}
            />

            <Input
                id={"beneficiado-input"}
                hasButton={false}
                buttonClass={""}
                label={"Beneficiado"}
                placeholder={"CPF do Beneficiado"}
                type={"search"}
                onChange={(e) => {
                    setSearch(e.target.value)
                }}
                classname={''}
            />

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
                />
                <Radio
                    title={"Beneficios"}
                    list={beneficiosFiltered}
                    subText={"Escolha somente um beneficio:"}
                    groupName={"beneficios"}
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
