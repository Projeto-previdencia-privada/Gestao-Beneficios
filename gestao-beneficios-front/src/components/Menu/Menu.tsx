import { useEffect, useRef } from 'react'
import { useNavigate } from 'react-router-dom'

const Menu = () => {
  const menuRef = useRef(null)
  const navigate = useNavigate()
  const menuItems = [
    {
      id: 1,
      icon: 'home',
      name: 'Página Inicial',
      url: 'http://localhost:8090/',
      isSpaLinkBehavior: true,
    },
    {
      id: 2,
      icon: 'database',
      name: 'Beneficios',
      isSpaLinkBehavior: true,
      list: [
        {
          id:5,
          icon: 'plus',
          name: 'Adicionar Beneficios',
          isSpaLinkBehavior: true,
          url: '/beneficio/cadastrar',
        },
        {
          id:6,
          icon: 'list',
          name: 'Beneficios Ativos',
          isSpaLinkBehavior: true,
          url: '/beneficio',
        }
      ]
    },
    {
      id: 3,
      icon: 'copy',
      name: 'Concessoes',
      isSpaLinkBehavior: true,
      list: [
        {
          id:7,
          icon: 'plus',
          name: 'Solicitar Concessoes',
          isSpaLinkBehavior: true,
          url: '/concessao/cadastrar',
        },
        {
          id:8,
          icon: 'list',
          name: 'Concessoes Ativas',
          isSpaLinkBehavior: true,
          url: '/concessao',
        }
      ]
    },
    {
      id:9,
      icon: 'info',
      name: 'Info',
      isSpaLinkBehavior: true,
      url: '/',
    }
  ]

  const navigateRouter = (route: any) => {
    const { detail: routerPath } = route
    navigate(routerPath[0], { replace: true })
  }

  useEffect(() => {
    const menuOnScreen = document.getElementById('main-navigation')
    menuOnScreen?.addEventListener('navigate', navigateRouter)
  }, [])

  return (
    <>
    <br-menu
      ref={menuRef}
      is-push={"true"}
      id="main-navigation"
      show-menu={"true"}
      list={JSON.stringify(menuItems)}
      data-breakpoints="col-sm-4 col-lg-3"
      density={"large"}
    >
    </br-menu>
      </>
  )
}

export default Menu
