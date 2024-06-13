
import {ChangeEvent, useEffect, useState} from "react";
import Input from "../components/Input/Input.tsx";


type data = {
    id: number
    nome: string
    tempoMinimo: string
    valorPercentual: string
    status: boolean
}
type beneficioMandado = {
    nome?: string | undefined
    tempoMinimo?: string | undefined
    valorPercentual?: string | undefined
}

const host: string | undefined = import.meta.env.VITE_HOST
const port: string | undefined = import.meta.env.VITE_PORT
const host_backend: string | undefined = import.meta.env.VITE_HOST_BACKEND
const port_backend: string | undefined = import.meta.env.VITE_PORT_BACKEND


const Beneficios = () =>{

    //UseStates da pagina
    const [beneficios, setBeneficios] = useState<data[]>([])
    const [message, setMessage] = useState({message:'', state: '', show: false})
    const [modificar, setModificar] = useState(false)
    const [dados, setDados] = useState<beneficioMandado>()
    const [search, setSearch] = useState('')
    const [isBeneficiosVazio, setIsBeneficiosVazio] = useState(false)

    const beneficiosFiltered = beneficios.filter((beneficio)=> beneficio.nome.startsWith(search))

    useEffect(() => {
        getBeneficios()
    },[modificar])


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
            .then(response => handleResponse(response)).then(data => setBeneficios(data))
    }

    const desativarBeneficio = (id: number) =>{
        fetch('http://'+host_backend+':'+port_backend+'/api/beneficio/'+id, {
            method: 'PATCH',
            headers: {
                'Accept': '*/*',
                'Access-Control-Allow-Origin': 'https://'+host+':'+port+'/'
            },
        } ).then(response => generateMessage(response))
    }

    const handleClick = (id:number) =>{
        fetch('http://'+host_backend+':'+port_backend+'/api/beneficio/'+id, {
            method: 'PUT',
            headers: {
                'Accept': '*/*',
                'Access-Control-Allow-Origin': 'https://'+host+':'+port+'/',
                'Content-Type': 'application/json'
            },body: JSON.stringify(dados)
        } ).then(response => generateMessage(response)).finally(getBeneficios)
    }


    const handleResponse = (response: Response) =>{
        if(response.status === 404){
            setIsBeneficiosVazio(true)
        }

        return response.json()
    }


    const generateMessage = (response: Response) => {
        if(response.status === 202 || response.status=== 200){
            setMessage({message:'Operacao concluida com sucesso!', show: true, state: "success"})
        }
        else if(response.status === 400){
            setMessage({message:'Beneficio ja esta inserido no sistema', show: true, state: "danger"})
        }
        else{
            setMessage({message:'Erro tente novamente mais tarde.', show: true, state: "danger"})
        }
    }


    const handleChange = (e: ChangeEvent<HTMLInputElement>,variavel:string) =>{
        setDados({...dados, [variavel]: e.target.value})
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
                label={"Procurar Beneficio"}
                placeholder={"Nome do Beneficio"}
                type={"search"}
                onChange={(e) => {
                    setSearch(e.target.value)
                }}
                classname={''}
                mask={''}
            />
            <br-list title="Lista de Beneficios" id="submited-users" data-toggle="true">
                {beneficiosFiltered.map((beneficio) => (
                    (beneficio.status && <br-item title={beneficio.nome} hover key={beneficio.id}>
                        <br-list>
                            <br-item hover>
                                {(!modificar && <div className="row align-items-center">
                                    <div className="col-11">
                                        <p><strong>Tempo Minimo</strong>: {beneficio.tempoMinimo} meses</p>
                                        <p><strong>Valor</strong>: {beneficio.valorPercentual}%</p>
                                    </div>
                                    <div className="">
                                    <br-button
                                            density={"small"}
                                            label="Desativar"
                                            icon="minus"
                                            type="primary"
                                            onClick={() => desativarBeneficio(beneficio.id)}
                                            aria-labelledby="Desativar Beneficio"
                                        ></br-button>
                                        <br-button
                                            density={"small"}
                                            label="Modificar"
                                            icon="edit"
                                            type="secondary"
                                            onClick={() => setModificar(true)}
                                            aria-labelledby="Modificar Beneficio"
                                        ></br-button>
                                    </div>
                                </div>)}
                                {(modificar && <div className="">
                                    <h5>Modificar Atributos do Beneficio "{beneficio.nome}"</h5>
                                    <Input
                                        id={"nome_input"}
                                        hasButton={false}
                                        buttonClass={""}
                                        label={"Beneficiado"}
                                        placeholder={"Nome do Beneficio"}
                                        type={"search"}
                                        onChange={(e) => handleChange(e, "nome")}
                                        classname={''}
                                        mask={''}
                                    />
                                    <Input
                                        id={"tempo_input"}
                                        hasButton={false}
                                        buttonClass={""}
                                        label={"Tempo Minimo"}
                                        placeholder={"Tempo Minimo de Contribuicao"}
                                        type={"search"}
                                        onChange={(e) => handleChange(e,"tempoMinimo")}
                                        classname={''}
                                        mask={''}
                                    />
                                    <Input
                                        id={"nome_input"}
                                        hasButton={false}
                                        buttonClass={""}
                                        label={"Valor"}
                                        placeholder={"Valor Percentual do Beneficio a ser convertido"}
                                        type={"search"}
                                        onChange={(e) => handleChange(e, "valorPercentual")}
                                        classname={''}
                                        mask={''}
                                    />
                                    <div className="">
                                        <br-button
                                            density={"small"}
                                            label="Salvar"
                                            icon="save"
                                            type="primary"
                                            onClick={() => handleClick(beneficio.id)}
                                            aria-labelledby="Salvar Edicao"
                                        ></br-button>
                                        <br-button
                                            density={"small"}
                                            label="Descartar"
                                            icon="edit"
                                            type="secondary"
                                            onClick={() => setModificar(false)}
                                            aria-labelledby="Modificar Beneficio"
                                        ></br-button>
                                    </div>
                                </div>)}
                            </br-item>
                        </br-list>
                    </br-item>)

                ))}
            </br-list>

            {isBeneficiosVazio && <br-message state={"warning"}>
                <center>Nao ha beneficios cadastrados!</center>
            </br-message>}


        </>

    )
}
export default Beneficios