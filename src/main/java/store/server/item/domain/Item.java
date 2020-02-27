package store.server.item.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import store.server.category.domain.Category;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String name;

    @Length(min = 5)
    private String description;

    @Valid
    @NotNull
    @Embedded
    private Price price;

    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date postDate;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<@NotNull Category> categories = new HashSet<>();

    public void initializePostDate() {
        this.postDate = new Date();
    }

}
