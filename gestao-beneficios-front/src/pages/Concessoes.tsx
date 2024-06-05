import Input from "../components/Input/Input.tsx";
import {useEffect, useState} from "react";

type data = {
    uuid: string
    requisitante: string
    beneficiado: string
    data: string
    valor: number
    status: boolean
    beneficios: beneficio
}

type beneficio = {
    id: number
    nome: string
    tempoMinimo: string
    valorPercentual: string
    status: boolean
}

const host: string | undefined = import.meta.env.VITE_HOST
const port: string | undefined = import.meta.env.VITE_PORT
const host_backend: string | undefined = import.meta.env.VITE_HOST_BACKEND
const port_backend: string | undefined = import.meta.env.VITE_PORT_BACKEND


const Concessoes = () =>{
    const [concessoes, setConcessoes] = useState<data[]>([])
    const [message, setMessage] = useState({message:'', state: '', show: false})
    const [search, setSearch] = useState('')

    useEffect(() => {
        getConcessoes()
    }, []);

    const concessoesBeneficiadoFiltered = concessoes.filter((concessao)=> concessao.beneficiado.startsWith(search))

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
            .then(response => response.json()).then(data => setConcessoes(data))
    }

    const desativarConcessao = async (uuid: string) => {
        await fetch('http://'+host_backend+':'+port_backend+'/api/concessao' + uuid, {
            method: 'PATCH',
            headers: {
                'Accept': '*/*',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': 'https://'+host+':'+port+'/'
            }
        })
            .then(response => generateMessage(response))
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
            />
            <br-list title="Lista de Concessoes" id="submited-users" data-toggle="true">
                {concessoesBeneficiadoFiltered.map((concessao) => (
                    (concessao.status && <br-item title={concessao.requisitante + concessao.uuid} hover key={concessao.uuid}>
                        <br-list>
                            <br-item hover>
                                <div className="row align-items-center">
                                    <div className="col-11">
                                        <p><strong>Requisitante:</strong>: {concessao.requisitante} meses</p>
                                        <p><strong>Beneficiado</strong>: {concessao.beneficiado}%</p>
                                        <p><strong>Valor</strong>: {concessao.valor}%</p>
                                        <p><strong>Beneficio</strong>: {concessao.beneficios.nome}%</p>
                                    </div>
                                    <div className="">
                                    <br-button
                                            density={"small"}
                                            label="Desativar"
                                            icon="minus"
                                            type="primary"
                                            onClick={() => desativarConcessao(concessao.uuid)}
                                            aria-labelledby="Desativar Concessao"
                                        ></br-button>
                                    </div>
                                </div>
                            </br-item>
                        </br-list>
                    </br-item>)

                ))}
            </br-list>
        </>
    )
}

export default Concessoes