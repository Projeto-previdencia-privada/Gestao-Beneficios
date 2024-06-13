import { ChangeEventHandler} from "react";
import InputMask from 'react-input-mask'



interface InputProps{
    id:string
    hasButton: boolean
    buttonClass: string
    label: string
    placeholder:string
    type: string
    classname: string
    onChange: ChangeEventHandler<HTMLInputElement>
    mask: string
}

const Input = ({mask,hasButton,buttonClass,label,placeholder,id,type, classname,onChange}:InputProps) =>{

    return(
        <>
            <div className={classname}>
                <div className="br-input large input-button">
                    <label htmlFor={id}>{label}</label>
                    <InputMask id={id} type={type} placeholder={placeholder} onChange={onChange} mask={mask} alwaysShowMask={false} maskPlaceholder={''}/>
                    {hasButton && <button className="br-button" type="button" aria-label="Buscar">
                        <i className={buttonClass} aria-hidden="true"></i>
                    </button>}
                </div>
            </div>
        </>
    )
}
export default Input