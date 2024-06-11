package unae.lp3.FacatAPP.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unae.lp3.FacatAPP.model.Oportunidad;
import unae.lp3.FacatAPP.repository.OportunidadRepository;

@Service
public class OportunidadServicioImpl  implements OportunidadServicio{


    @Autowired
    private OportunidadRepository oportunidadRepository; 

    @Override
    public void guardarOportunidad(Oportunidad oportunidad) {
        oportunidadRepository.save(oportunidad); 
    }

}


