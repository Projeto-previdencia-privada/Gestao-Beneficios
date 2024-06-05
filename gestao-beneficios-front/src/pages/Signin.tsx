import {ChangeEvent, FormEvent, useState} from 'react'

const SignInPage = () => {
  const [message, setMessage] = useState({
    show: 'true',
    message: 'Lembre-se que este e um projeto pessoal para simular um sistema previdenciario, logo nao insira informacoes reais.',
    state: 'info'
  })
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    const data = {
      grant_type: "password",
      client_id: "login-app",
      username: username,
      password: password
    }

    const body = new URLSearchParams()
    body.append("grant_type", data.grant_type)
    body.append("client_id", data.client_id)
    body.append("username", data.username)
    body.append("password", data.password)
      fetch('http://localhost:9080/realms/previdencia/protocol/openid-connect/token',
        {
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded',
          },
          body: body, mode: 'no-cors'
        }).then(res => res.json())
        .catch(error => errorToken(error))
  }


  const errorToken = (error: unknown) => {
    setMessage({message: 'Erro no login tente novamente mais tarde', state: 'warning', show: 'true'})
    console.log(error)
  }


  return (
      <>
        {(message.show && <br-message
            state={message.state}
            show-icon={true}>
          {message.message}
        </br-message>)}
          <div className="col-sm-6 col-md-6 col-lg-3">
                Realize o login no sistema:
                <form onSubmit={handleSubmit}>
                  <div className="br-input input-inline">
                    <input
                        placeholder="Usuario"
                        onChange={(e: ChangeEvent<HTMLInputElement>) => setUsername(e.target.value)}>
                    </input>
                  </div>
                  <div className="br-input input-inline">
                    <input
                        type="password"
                        placeholder="Senha"
                        onChange={(e:ChangeEvent<HTMLInputElement>) => setPassword(e.target.value)}>
                    </input>
                  </div>
                    <br-button
                        icon="angle-right"
                        label="Entrar"
                        type="primary"
                        onClick={handleSubmit}
                    ></br-button>
                </form>
          </div>
</>
)
}

export default SignInPage
