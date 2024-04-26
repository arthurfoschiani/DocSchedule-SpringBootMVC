package br.com.fiap.docschedule.consulta;

import java.time.LocalDate;

import br.com.fiap.docschedule.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{consulta.especialidade.notblank}")
    private String especialidade;

    private String motivo;

    @NotNull(message = "{consulta.dataConsulta.notnull}")
    @Future(message = "{consulta.dataConsulta.future}")
    private LocalDate dataConsulta;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
