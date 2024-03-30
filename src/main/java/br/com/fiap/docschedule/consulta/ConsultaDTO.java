package br.com.fiap.docschedule.consulta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaDTO {
    private Long id;
    private String especialidade;
    private String motivo;
    private String dataConsulta;
}
