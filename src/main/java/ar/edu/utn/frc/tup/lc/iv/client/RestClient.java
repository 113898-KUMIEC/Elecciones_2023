package ar.edu.utn.frc.tup.lc.iv.client;

import ar.edu.utn.frc.tup.lc.iv.models.Cargos;
import ar.edu.utn.frc.tup.lc.iv.models.Distritos;
import ar.edu.utn.frc.tup.lc.iv.models.Resultados;
import ar.edu.utn.frc.tup.lc.iv.models.Secciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestClient {
    @Autowired
    RestTemplate restTemplate;

    private final String URL = "http://localhost:8080";

    public ResponseEntity<List<Distritos>> getAllDistritos(){
        return restTemplate.exchange(URL + "/distritos", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Distritos>>() {});
    }
    public ResponseEntity<List<Distritos>> getDistritosByName(String nombre){
        return restTemplate.exchange(URL + "/distritos?distritoNombre=" + nombre, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Distritos>>() {});
    }
    public ResponseEntity<List<Distritos>> getDistritosById(Long id){
        return restTemplate.exchange(URL + "/distritos?distritoId=" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Distritos>>() {});
    }
    public ResponseEntity<List<Cargos>> getCargosByDistrito(Long id){
        return restTemplate.exchange(URL + "/cargos?distritoId=" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Cargos>>() {});
    }
    public ResponseEntity<List<Secciones>> getSeccionesByIdDistrito(Long id){
        return restTemplate.exchange(URL + "/secciones?distritoId=" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Secciones>>() {});
    }

    public ResponseEntity<List<Secciones>> getSeccionesByIdDistritoAndIdSeccion(Long idSeccion,Long idDistrito){
        return restTemplate.exchange(URL + "/secciones?seccionId="+idSeccion+"&distritoId=" + idDistrito, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Secciones>>() {});
    }

    public ResponseEntity<List<Resultados>> getResultadosByIdSeccion(Long idSeccion){
        return restTemplate.exchange(URL + "/resultados?seccionId="+idSeccion, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Resultados>>() {});
    }

}
