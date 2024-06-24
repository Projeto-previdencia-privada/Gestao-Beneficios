const Footer = () => {
  const license: string = 'Todo o conteúdo deste site está publicado sob a licença MIT'
  const logo = {
    url: '//upload.wikimedia.org/wikipedia/commons/thumb/1/11/Gov.br_logo.svg/250px-Gov.br_logo.svg.png',
    description: 'Logo Gov.br',
  }
  const categorias =[
    {
      title: 'Outras Gestoes RPPS',
      items: [
        {
          title: 'Gestao de Contribuintes',
          href: 'http://192.168.37.8:8090/login'
        },
        {
          title: 'Gestao de Contribuicoes',
          href: 'http://192.168.37.8:3000'
        },
        {
          title: 'Gestao de Emprestimos',
          href: 'http://192.168.37.8:8080'
        }
      ]
    }
  ]


  return <br-footer
      text={license}
      logo={JSON.stringify(logo)}
      id="footer"
      container-fluid="true"
      categories={JSON.stringify(categorias)}
  ></br-footer>
}

export default Footer
