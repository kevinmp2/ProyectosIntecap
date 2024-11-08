import { BrowserRouter, Route, Routes } from "react-router-dom";
import ListadoEmpleados from "./empleados/ListadoEmpleados";
import Navegacion from "./plantilla/Navegacion";
import AgregarEmpleado from "./empleados/AgregarEmpleado";
import EditarEmpleado from "./empleados/EditarEmpleado";


function App() {
  return (
    <div className="container">
      <BrowserRouter>
        <Navegacion></Navegacion>
        <Routes>
          <Route exact path="/" element= {<ListadoEmpleados></ListadoEmpleados>}></Route>
          <Route exact path="/agregar" element= {<AgregarEmpleado></AgregarEmpleado>}></Route>
          <Route exact path="/editar/:id" element= {<EditarEmpleado></EditarEmpleado>}></Route>
        </Routes>
      </BrowserRouter>
    </div>

  );
}

export default App;
