package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistritoAndCargoDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistritoDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.ResumenDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.SeccionDTO;
import ar.edu.utn.frc.tup.lc.iv.models.Distritos;
import ar.edu.utn.frc.tup.lc.iv.service.EleccionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
public class EleccionesController {
    @Autowired
    private EleccionesService eleccionesService;

    @GetMapping("/distritos")
    public ResponseEntity<List<DistritoDTO>> getAllFormaciones(@RequestParam(required = false) String distrito_nombre) {
        if (distrito_nombre == null){
            return ResponseEntity.ok(eleccionesService.getAllDistritos());
        }else {
            return ResponseEntity.ok(eleccionesService.getDistritoByName(distrito_nombre));
        }
    }
    @GetMapping("/cargos")
    public ResponseEntity<List<DistritoAndCargoDTO>> getAllCargosAndDistrito(@RequestParam(required = false) Long distrito_id) {
        return ResponseEntity.ok(eleccionesService.getDistritoAndCargo(distrito_id));
    }
    @GetMapping("/secciones")
    public ResponseEntity<List<SeccionDTO>> getAllSecciones(@RequestParam() Long distrito_id,@RequestParam(required = false) Long seccion_id) {
        if (seccion_id != null){
            return ResponseEntity.ok(eleccionesService.getSeccionByIdDistritoAndIdSeccion(seccion_id,distrito_id));
        }else {
            return ResponseEntity.ok(eleccionesService.getSeccionByIdDistrito(distrito_id));
        }
    }
    @GetMapping("/resultados")
    public ResponseEntity<List<ResumenDTO>> getAllResumen(@RequestParam() Long distrito_id, @RequestParam(required = false) Long seccion_id) {
            return ResponseEntity.ok(eleccionesService.getResumenPorAgrupacion(seccion_id,distrito_id));
    }
}
