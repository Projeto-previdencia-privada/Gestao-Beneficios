import {ChangeEventHandler} from "react";

interface RadioProps{
    title: string
    list: {nome: string }[]
    subText: string
    groupName: string
    onChange: ChangeEventHandler<HTMLInputElement>
}

const Radio=({onChange,title, list, subText, groupName}:RadioProps)=>{

    return (
        <>
            <p className="label mb-0">{title}</p>
            <p className="help-text">{subText}</p>
            {list.map((list) => (
                <div className="br-radio">
                    <input id={list.nome} type="radio" name={groupName} value={list.nome} onChange={onChange} />
                    <label htmlFor={list.nome}>{list.nome}</label>
                </div>
            ))
            }
        </>
    )
}

export default Radio
