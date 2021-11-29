package leadmentoring.desafiowebbackend.repository;

import leadmentoring.desafiowebbackend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,Long> {

    public List<Users> findByEmail(String email);

    public List<Users> findByCpf(String cpf);
}
