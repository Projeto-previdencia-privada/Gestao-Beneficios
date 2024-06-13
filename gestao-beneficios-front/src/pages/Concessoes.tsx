import Input from "../components/Input/Input.tsx";
import {useEffect, useState} from "react";

type data = {
    id: string
    requisitante: string
    beneficiado: string
    data: string
    valor: number
    status: boolean
    beneficioNome: string
}


const host: string | undefined = import.meta.env.VITE_HOST
const port: string | undefined = import.meta.env.VITE_PORT
const host_backend: string | undefined = import.meta.env.VITE_HOST_BACKEND
const port_backend: string | undefined = import.meta.env.VITE_PORT_BACKEND


const Concessoes = () =>{
    const [concessoes, setConcessoes] = useState<data[]>([])
    const [message, setMessage] = useState({message:'', state: '', show: false})
    const [search, setSearch] = useState('')
    const [isConcessaoVazia, setIsConcessaoVazia] = useState(false)

    useEffect(() => {
        getConcessoes()
    }, []);

    const concessoesBeneficiadoFiltered = concessoes.filter((data) => data.beneficiado.toString().startsWith(search))

    async function getConcessoes() {
        await fetch('http://'+host_backend+':'+port_backend+'/api/concessao', {
            method: "GET",
            headers: {
                'Accept': '*/*',
                'Access-Control-Allow-Origin': 'https://'+host+':'+port+'/'
            },
            mode: "cors",
            cache: "default",
        })
            .then(response => handleResponse(response)).then(data => setConcessoes(data))
    }

    const desativarConcessao = async (uuid: string) => {
        await fetch('http://'+host_backend+':'+port_backend+'/api/concessao/' + uuid, {
            method: 'PATCH',
            headers: {
                'Accept': '*/*',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': 'https://'+host+':'+port+'/'
            }
        })
            .then(response => generateMessage(response))
    }

    const handleResponse = (response: Response) =>{
        if(response.status === 404){
            setIsConcessaoVazia(true)
        }

        return response.json()
    }

    const generateMessage = (response: Response) => {
        if(response.status === 202 || response.status=== 200){
            setMessage({message:'Operacao concluida com sucesso!', show: true, state: "success"})
        }
        else{
            setMessage({message:'Erro tente novamente mais tarde.', show: true, state: "danger"})
        }
    }
    return(
        <>
            {message.show && (<br-message
                state={message.state}
                closable={"true"}
                show-icon="true"
                message={message.message}
            ></br-message>)}

            <Input
                id={"nome-input"}
                hasButton={true}
                buttonClass={"fas fa-search"}
                label={"Procurar Concessao"}
                placeholder={"Nome do Requisitante"}
                type={"search"}
                onChange={(e) => {
                    setSearch(e.target.value)
                }}
                classname={''}
                mask={''}
            />
            <br-list title="Lista de Concessoes" id="submited-users" data-toggle="true">
                {concessoesBeneficiadoFiltered.map((concessao) => (
                    (concessao.status && <br-item title={'CPF '+concessao.requisitante+ ' --->  '+ concessao.beneficioNome} hover key={concessao.id}>
                        <br-list>
                            <br-item hover>
                                <div className="row align-items-center">
                                    <div className="col-11">
                                        <p><strong>Requisitante:</strong>: {concessao.requisitante}</p>
                                        <p><strong>Beneficiado</strong>: {concessao.beneficiado}</p>
                                        <p><strong>Valor</strong>: {concessao.valor}</p>
                                        <p><strong>Beneficio</strong>: {concessao.beneficioNome}</p>
                                        <p><strong>Data do Pedido</strong>: {concessao.data}</p>
                                    </div>
                                    <div className="">
                                    <br-button
                                            density={"small"}
                                            label="Desativar"
                                            icon="minus"
                                            type="primary"
                                            onClick={() => desativarConcessao(concessao.id)}
                                            aria-labelledby="Desativar Concessao"
                                        ></br-button>
                                    </div>
                                </div>
                            </br-item>
                        </br-list>
                    </br-item>)

                ))}
            </br-list>

            {isConcessaoVazia && <br-message state={"warning"}>
                <center>Nao ha concessoes cadastradas!</center>
            </br-message>}
        </>
    )
}

export default Concessoes