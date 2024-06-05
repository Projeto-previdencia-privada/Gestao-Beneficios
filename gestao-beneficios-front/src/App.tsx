import '@govbr-ds/core/dist/core.min.css'
import '@govbr-ds/webcomponents/dist/webcomponents.umd.min.js'
import Breadcrumb from './components/Breadcrumb/Breadcrumb'
import Footer from './components/Footer/Footer'
import Header from './components/Header/Header'
import Menu from './components/Menu/Menu'
import AddBeneficiosPage from './pages/AddBeneficios'
import Beneficios from './pages/Beneficios'
import HomePage from './pages/Home'
import SignInPage from './pages/Signin'
import { Route, Routes, useLocation } from 'react-router-dom'
import AddConcessoesPage from './pages/AddConcessoes'
import { useEffect, useState } from 'react'
import Concessoes from "./pages/Concessoes.tsx";

const App = () => {
    const [login, setLogin] = useState(true)
    const location = useLocation()
    useEffect(() => {
        if (location.pathname === '/sign-in') {
            setLogin(false)
        } else {
            setLogin(true)
        }
    })

    return (
        <div className="template-base">
            {(login && <Header />)}
            <main className="d-flex flex-fill mb-5" id="main">
                <div className="container-fluid">
                    <div className="row">
                        {(login && <div className="col-sm-4 col-lg-3">
                            <Menu />
                        </div>)}
                        <div className="col mb-5">
                            <Breadcrumb />
                            <div className="main-content pl-sm-3 mt-4" id="main-content">
                                <Routes>
                                    <Route path="/" element={<HomePage />} />
                                    <Route path="/beneficio/cadastrar" element={<AddBeneficiosPage />} />
                                    <Route path="/beneficio" element={<Beneficios />} />
                                    <Route path="/concessao" element={<Concessoes />} />
                                    <Route path="/sign-in" element={<SignInPage />} />
                                    <Route path="/concessao/cadastrar" element={<AddConcessoesPage />} />
                                </Routes>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <Footer />
        </div>
    )
}

export default App
