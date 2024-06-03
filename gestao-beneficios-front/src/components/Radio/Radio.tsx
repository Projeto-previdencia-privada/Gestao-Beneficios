interface RadioProps{
    title: string
    list: {nome: string }[]
    subText: string
    groupName: string
}

const Radio=({ title, list, subText, groupName}:RadioProps)=>{

    return (
        <>
            <p className="label mb-0">{title}</p>
            <p className="help-text">{subText}</p>
            {list.map((list, index) => (
                <div className="br-radio">
                    <input id={list.nome+""+index} type="radio" name={groupName} value={list.nome}/>
                    <label htmlFor={list.nome+""+index}>{list.nome}</label>
                </div>
            ))
            }
        </>
    )
}

export default Radio
