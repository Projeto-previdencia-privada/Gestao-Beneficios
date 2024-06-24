import { useState } from 'react'

const home: string = "http://localhost:8090/"

const Header = () => {
  const [isOpen, setIsOpen] = useState(true)

  const toggleMenu = () => {
    setIsOpen(!isOpen)
  }


  const title = 'Regime Próprio de Previdência Social'
  const signature = 'Sistema de Gestao de Beneficios'
  const image = {
    src: '//upload.wikimedia.org/wikipedia/commons/thumb/1/11/Gov.br_logo.svg/250px-Gov.br_logo.svg.png',
    alt: 'Logo de Gov.br',
  }

  const links = [{}
  ]

  const functions = [
    {
      icon: 'home',
      iconFamily: 'fab',
      name: 'Github do projeto',
      url: 'https://github.com/Projeto-previdencia-privada',
      tooltipText: 'Github do projeto com as outras gestoes',
      tooltipPlace: 'bottom',
    },
  ]

  return (
    <br-header
      alt={image.alt}
      container-fluid="true"
      isPush="true"
      has-menu="true"
      has-title-link="true"
      id="header"
      image= '//upload.wikimedia.org/wikipedia/commons/thumb/1/11/Gov.br_logo.svg/250px-Gov.br_logo.svg.png'
      signature={signature}
      title-link-url={home}
      title={title}
    >
      <div slot="headerMenu">
        <br-button
          role="option"
          circle
          density="small"
          aria-label="Menu"
          icon={isOpen ? 'times' : 'bars'}
          data-toggle="menu"
          data-target="#main-navigation"
          onClick={toggleMenu}
        ></br-button>
      </div>
      <br-header-action
        slot="headerAction"
        title-links="Bibliotecas de componentes"
        hasLogin={"true"}
        link-login={"/sign-in"}
        list-links={JSON.stringify(links)}
        title-functions="Links relacionados"
        list-functions={JSON.stringify(functions)}
        has-login="true"
        label-login={"Entrar"}
      ></br-header-action>
    </br-header>
  )
}

export default Header
