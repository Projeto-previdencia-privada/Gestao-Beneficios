import {useEffect, useState} from "react";
import ReactMarkdown from 'react-markdown'


const HomePage = () => {
    const [markdownContent, setMarkdownContent] = useState('')
    useEffect(() => {
        fetch('./../../README.md')
            .then((response) => response.text())
            .then((data) => setMarkdownContent(data))
            .catch((error) => console.error('Erro ao carregar o arquivo Markdown Read.me:', error))
    }, [])


    return (
        <div>
            <ReactMarkdown>{markdownContent}</ReactMarkdown>
        </div>
    )

}

export default HomePage
