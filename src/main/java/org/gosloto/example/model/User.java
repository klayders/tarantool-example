package org.gosloto.example.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usr", indexes = {
    @Index(columnList = "login", name = "user_login_index"),
    @Index(columnList = "email", name = "user_email_index")
})
public class User {

  @Id
  private long id;

  private String first_name;
  private String last_name;

  private String login;
  private String email;

  public static User fromTarantool(List<?> resultList) {
    if (!CollectionUtils.isEmpty(resultList)) {
      final var row = (List) resultList.get(0);
      return User.builder()
          .id((Long.valueOf((Integer) row.get(0))))
          .first_name((String) row.get(1))
          .last_name((String) row.get(2))
          .login((String) row.get(3))
          .email((String) row.get(4))
          .build();
    }else {
      return new User();
    }
  }
}
