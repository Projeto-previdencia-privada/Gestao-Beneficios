import { MouseEventHandler} from "react";

interface RadioProps{
    title: string
    list: {nome: string }[]
    subText: string
    groupName: string
    onClick: MouseEventHandler<HTMLInputElement>
}

const Radio=({ onClick,title, list, subText, groupName}:RadioProps)=>{

    return (
        <>
            <p className="label mb-0">{title}</p>
            <p className="help-text">{subText}</p>
            {list.map((list, index) => (
                <div className="br-radio">
                    <input id={list.nome+""+index} type="radio" name={groupName} value={list.nome} onClick={onClick}/>
                    <label htmlFor={list.nome+""+index}>{list.nome}</label>
                </div>
            ))
            }
        </>
    )
}

export default Radio
