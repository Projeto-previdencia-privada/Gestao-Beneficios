import { useState } from 'react'

const home: string = "http://192.168.37.8:8090"

const Header = () => {
  const [isOpen, setIsOpen] = useState(false)

  const toggleMenu = () => {
    setIsOpen(!isOpen)
  }


  const title = 'Sistema de Gestão de Benefícios'
  const signature = 'Sistema de Regime Próprio de Previdência Social'
  const image = {
    src: '//upload.wikimedia.org/wikipedia/commons/thumb/1/11/Gov.br_logo.svg/250px-Gov.br_logo.svg.png',
    alt: 'Logo de Gov.br',
  }

  const links = [
    {
      name: 'Orgãos do Governo',
      href: 'https://www.gov.br/pt-br/orgaos-do-governo',
      title: 'Orgãos do Governo',
      target: '_blank',
    },
    {
      name: 'Acesso à Informação',
      href: 'https://www.gov.br/acessoainformacao/pt-br',
      title: 'Acesso à Informação',
      target: '_blank',
    },
    {
      name: 'Legislação',
      href: 'http://www4.planalto.gov.br/legislacao',
      title: 'Legislação',
      target: '_blank',
    },
    {
      name: 'Acessibilidade',
      href: 'https://www.gov.br/governodigital/pt-br/acessibilidade-e-usuario/acessibilidade-digital',
      title: 'Acessibilidade',
      target: '_blank',
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
        list-links={JSON.stringify(links)}
        title-functions="Links relacionados"
        label-login={"Entrar"}
      ></br-header-action>
    </br-header>
  )
}

export default Header
