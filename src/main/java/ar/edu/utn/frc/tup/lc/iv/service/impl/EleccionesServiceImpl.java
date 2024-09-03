package ar.edu.utn.frc.tup.lc.iv.service.impl;

import ar.edu.utn.frc.tup.lc.iv.client.RestClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.*;
import ar.edu.utn.frc.tup.lc.iv.models.Cargos;
import ar.edu.utn.frc.tup.lc.iv.models.Distritos;
import ar.edu.utn.frc.tup.lc.iv.models.Resultados;
import ar.edu.utn.frc.tup.lc.iv.models.Secciones;
import ar.edu.utn.frc.tup.lc.iv.service.EleccionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
@Service
public class EleccionesServiceImpl implements EleccionesService {
    @Autowired
    private RestClient restClient;
    ArrayList<String> agrupacion = new ArrayList<>();
    public List<Distritos> getDistritos(){
        return restClient.getAllDistritos().getBody();
    }

    @Override
    public List<DistritoDTO> getAllDistritos() {
        List<Distritos> distritos = getDistritos();
        List<DistritoDTO> response =new ArrayList<>();

        for (Distritos d:distritos){
            response.add(new DistritoDTO(d.getDistritoId(),d.getDistritoNombre()));
        }
        return response;
    }

    @Override
    public List<DistritoDTO> getDistritoByName(String name) {
        List<Distritos> distritos = restClient.getDistritosByName(name).getBody();
        List<DistritoDTO> response =new ArrayList<>();

        if (distritos != null) {
            for (Distritos d:distritos){
                response.add(new DistritoDTO(d.getDistritoId(),d.getDistritoNombre()));
            }
        }
        return response;
    }
    public List<DistritoDTO> getDistritoById(Long id) {
        List<Distritos> distritos = restClient.getDistritosById(id).getBody();
        List<DistritoDTO> response =new ArrayList<>();

        if (distritos != null) {
            for (Distritos d:distritos){
                response.add(new DistritoDTO(d.getDistritoId(),d.getDistritoNombre()));
            }
        }
        return response;
    }

    public List<CargoDTO> getCargoById(Long id) {
        List<Cargos> cargos = restClient.getCargosByDistrito(id).getBody();
        List<CargoDTO> response =new ArrayList<>();

        if (cargos != null) {
            for (Cargos c:cargos){
                response.add(new CargoDTO(c.getCargoId(),c.getCargoNombre()));
            }
        }
        return response;
    }

    @Override
    public List<DistritoAndCargoDTO> getDistritoAndCargo(Long id) {
        List<DistritoDTO> distritos = getDistritoById(id);
        List<CargoDTO> cargos = getCargoById(id);

        List<DistritoAndCargoDTO> response = new ArrayList<>();
        if (distritos != null && cargos != null){
           for (DistritoDTO d:distritos){
                   response.add(new DistritoAndCargoDTO(d,cargos));
           }
        }
        return response;
    }
    @Override
    public List<SeccionDTO> getSeccionByIdDistrito(Long id) {
        List<Secciones> secciones = restClient.getSeccionesByIdDistrito(id).getBody();

        List<SeccionDTO> response = new ArrayList<>();
        if (secciones != null){
            for (Secciones s:secciones){
                response.add(new SeccionDTO(s.getSeccionId(),s.getSeccionNombre()));
            }
        }
        return response;
    }
    @Override
    public List<SeccionDTO> getSeccionByIdDistritoAndIdSeccion(Long idSeccion, Long idDistrito) {
        List<Secciones> secciones = restClient.getSeccionesByIdDistritoAndIdSeccion(idSeccion,idDistrito).getBody();

        List<SeccionDTO> response = new ArrayList<>();
        if (secciones != null){
            for (Secciones s:secciones){
                response.add(new SeccionDTO(s.getSeccionId(),s.getSeccionNombre()));
            }
        }
        return response;
    }
    @Override
    public List<ResumenDTO> getResumenPorAgrupacion(Long idSeccion, Long idDistrito) {
        List<Resultados> resultados = restClient.getResultadosByIdSeccion(idSeccion).getBody();
        List<DistritoDTO> distrito = getDistritoById(idDistrito);
        List<SeccionDTO> seccion = getSeccionByIdDistritoAndIdSeccion(idSeccion,idDistrito);

        Long orden = 0L;

        agregarAgrupaicones();
        List<ResumenDTO> response = new ArrayList<>();

        List<ResultadoDTO> resultadoDTOList = new ArrayList<>();

        for (String s : agrupacion) { //doy vuelta por los 9 casos que pueden ser
            ResultadoDTO resultadoDTO = new ResultadoDTO(0L, "",0L,0f);
            orden++;
            if (resultados != null) {
                for (Resultados r : resultados) {
                    if (r.getAgrupacionNombre().equals(s)) {
                        resultadoDTO.setNombre(r.getAgrupacionNombre());
                        resultadoDTO.setVotos(resultadoDTO.getVotos() + r.getVotosCantidad());
                        //se puede hacer con bigdecimal
//                        double porcentaje = (double) resultadoDTO.getVotos() / devolverTotalVotos(resultados);
//                        resultadoDTO.setPorcentje(new BigDecimal(porcentaje).setScale(4, RoundingMode.HALF_UP));
                        //-------------------------
                        resultadoDTO.setPorcentje(Math.round(resultadoDTO.getVotos().floatValue()/devolverTotalVotos(resultados) * 10000f) / 10000f);
                        //-------------------------
                        resultadoDTO.setOrden(orden);
                    }else if (r.getVotosTipo().equals(s)){
                        resultadoDTO.setNombre(r.getVotosTipo());
                        resultadoDTO.setVotos(resultadoDTO.getVotos() + r.getVotosCantidad());
                        resultadoDTO.setPorcentje(Math.round(resultadoDTO.getVotos().floatValue()/devolverTotalVotos(resultados) * 10000f) / 10000f);
                        resultadoDTO.setOrden(orden);
                    }
                }
               resultadoDTOList.add(resultadoDTO);
            }
        }

        // Ordena la lista por votos en orden descendente
        resultadoDTOList.sort((r1, r2) -> r2.getVotos().compareTo(r1.getVotos()));

        // Asigna el número de orden basado en la posición en la lista ordenada
        for (int i = 0; i < resultadoDTOList.size(); i++) {
            ResultadoDTO resultadoDTO = resultadoDTOList.get(i);
            resultadoDTO.setOrden((long) (i + 1));
        }
        response.add(new ResumenDTO(distrito.stream().findFirst().get().getNombre(),
                seccion.stream().findFirst().get().getNombre(),
                resultadoDTOList));
        return response;
    }
    public float devolverTotalVotos(List<Resultados> resultados){
        float total = 0;
        for (Resultados r:resultados){
            total += r.getVotosCantidad();
        }
        return total;
    }

    public void agregarAgrupaicones(){
        agrupacion.add("UNION POR LA PATRIA");
        agrupacion.add("LA LIBERTAD AVANZA");
        agrupacion.add("JUNTOS POR EL CAMBIO");
        agrupacion.add("HACEMOS POR NUESTRO PAIS");
        agrupacion.add("FRENTE DE IZQUIERDA Y DE TRABAJADORES - UNIDAD");
        agrupacion.add("EN BLANCO");
        agrupacion.add("NULO");
        agrupacion.add("IMPUGNADO");
        agrupacion.add("RECURRIDO");
    }
}
