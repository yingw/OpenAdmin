package cn.yinguowei.oa.entity;

import cn.yinguowei.oa.entity.support.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Random;

/**
 * @author yinguowei 2018/4/1.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class User extends AbstractAuditingEntity {
    private static final Random RANDOM = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull @NotNull @NotEmpty @Column(unique = true)
    String username;

    @NotNull @NotEmpty @NonNull
    String fullname;

    @JsonIgnore
    String password;

    @NonNull @NotEmpty @NotNull
    String email;

    @Enumerated(EnumType.STRING)
    Gender gender = Gender.randomGender();

    private Boolean active = RANDOM.nextBoolean();

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles = new HashSet<>();

    public boolean isNew() {
        return this.id == null;
    }

}