package br.com.fiap.docschedule.consulta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.docschedule.user.User;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    List<Consulta> findAllByUser(User user);
}
