import { useLocation } from 'react-router-dom'
const Breadcrumb = () => {
  const location = useLocation()

  let isHome = false
  let label
  const links: any[] = [
    {
      label: 'Página Inicial',
      url: '/',
      home: true,
    },
  ]

  if (location.pathname === '/sign-in') label = 'Sign-In'
  if (location.pathname === '/beneficio') label = 'Listagem de Beneficios'
  if (location.pathname === '/beneficio/cadastrar') label = 'Cadastrar Beneficios'
  if (location.pathname === '/concessao/cadastrar') label = 'Solicitar Concessoes'
  if (location.pathname === '/concessao') label = 'Listagem de Concessoes'

  links.push({
    label: label,
    url: location.pathname,
    active: true,
  })

  if (location.pathname === '/') isHome = true

  return !isHome ? <br-breadcrumb label="Breadcrumb" links={JSON.stringify(links)}></br-breadcrumb> : null
}

export default Breadcrumb
