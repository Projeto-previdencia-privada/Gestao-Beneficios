import { useState } from 'react'
const Header = () => {
  const [isOpen, setIsOpen] = useState(true)

  const toggleMenu = () => {
    setIsOpen(!isOpen)
  }

  const title = 'Regime Próprio de Previdência Social'
  const signature = 'Sistema de Gestao de Beneficios'
  const image = {
    src: '/bibliotecas/wc/govbr-ds-wc-quickstart-react/logo-react.png',
    alt: 'Logo de exemplo',
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
      image= 'https://play-lh.googleusercontent.com/1C3J4NDQbo3k8BBZswaPcaW_8IGouIsXvUVPBR_Bp7mVFplOXMtGGGF3W4o5V0g5DO0M'
      signature={signature}
      title-link-url="/bibliotecas/wc/govbr-ds-wc-quickstart-react/"
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
