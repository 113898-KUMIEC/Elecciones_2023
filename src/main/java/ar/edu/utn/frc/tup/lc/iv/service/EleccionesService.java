package ar.edu.utn.frc.tup.lc.iv.service;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistritoAndCargoDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistritoDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.ResumenDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.SeccionDTO;
import ar.edu.utn.frc.tup.lc.iv.models.Distritos;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EleccionesService {
    List<DistritoDTO> getAllDistritos();
    List<DistritoDTO> getDistritoByName(String name);

    List<DistritoAndCargoDTO> getDistritoAndCargo(Long id);

    List<SeccionDTO> getSeccionByIdDistrito(Long id);

    List<SeccionDTO> getSeccionByIdDistritoAndIdSeccion(Long idSeccion, Long idDistrito);

    List<ResumenDTO> getResumenPorAgrupacion(Long idSeccion, Long idDistrito);
}
