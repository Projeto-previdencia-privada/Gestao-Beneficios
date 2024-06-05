import { ChangeEventHandler} from "react";

interface InputProps{
    id:string
    hasButton: boolean
    buttonClass: string
    label: string
    placeholder:string
    type: string
    classname: string
    onChange: ChangeEventHandler<HTMLInputElement>
}

const Input = ({hasButton,buttonClass,label,placeholder,id,type, classname,onChange}:InputProps) =>{

    return(
        <>
            <div className={classname}>
                <div className="br-input large input-button">
                    <label htmlFor={id}>{label}</label>
                    <input id={id} type={type} placeholder={placeholder} onChange={onChange}/>
                    {hasButton && <button className="br-button" type="button" aria-label="Buscar">
                        <i className={buttonClass} aria-hidden="true"></i>
                    </button>}
                </div>
            </div>
        </>
    )
}
export default Input