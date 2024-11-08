import axios from 'axios';
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';

export default function AgregarEmpleado() {

    let navegacion = useNavigate();

    const [empleado, setEmpleado] = useState({
        nombre:"",
        departamento:"",
        sueldo:""
    });

    const{nombre, departamento, sueldo} = empleado;

    const oninputChange = (e) => {
        setEmpleado({...empleado, [e.target.name]: e.target.value})
    }

    const onSubmit = async (e) => {
        e.preventDefault();
        const urlBase = "http://localhost:8084/recursoshumanos-app/empleados";
        await axios.post(urlBase, empleado);
        navegacion("/")

    }

  return (
    <div className='conteiner'>
        <div className='container text-center' style={{margin: "30px"}}>
            <div>Agregar empleado</div>
        </div>
        <form onSubmit={(e) => onSubmit(e)}>
                <div className="mb-3">
                    <label htmlFor="nombre" className="form-label">Nombre</label>
                    <input type="text" className="form-control" id="nombre" name='nombre' required={true} value={nombre} onChange={(e) => oninputChange(e)}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="departamento" className="form-label">Departamento</label>
                    <input type="text" className="form-control" id="departamento" name='departamento' value={departamento} onChange={(e) => oninputChange(e)}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="sueldo" className="form-label">Sueldo</label>
                    <input type="number" className="form-control" id="sueldo" name='sueldo'  value={sueldo} onChange={(e) => oninputChange(e)}/>
                </div>
                <div className='text-center'>
                    <button type="submit" className="btn btn-warning btn-sm me-3">Agregar</button>
                    <a href='/' className='btn btn-danger btn-sm'>Regresar</a>
                </div>
        </form>
        
    </div>
  )
}
