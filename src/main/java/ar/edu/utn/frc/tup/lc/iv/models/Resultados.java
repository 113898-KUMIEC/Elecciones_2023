package ar.edu.utn.frc.tup.lc.iv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resultados {
    private Long id;
    private String eleccionTipo;
    private String recuentoTipo;
    private String padronTipo;
    private Long distritoId;
    private String distritoNombre;
    private Long seccionProvincialId;
    private String seccionProvincialNombre;
    private Long seccionId;
    private String seccionNombre;
    private String circuitoId;
    private String circuitoNombre;
    private Long mesaId;
    private String mesaTipo;
    private int mesaElectores;
    private Long cargoId;
    private String cargoNombre;
    private Long agrupacionId;
    private String agrupacionNombre;
    private String listaNumero;
    private String listaNombre;
    private String votosTipo;
    private Long votosCantidad;
    private int año;
}
