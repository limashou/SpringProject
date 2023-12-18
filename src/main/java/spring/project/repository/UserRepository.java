package spring.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.project.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("from User oc where oc.username = :username")
    User getUserByUsername(String username);
    @Query("from User oc where oc.role = 'USER'")
    List<User> getUser();

}
