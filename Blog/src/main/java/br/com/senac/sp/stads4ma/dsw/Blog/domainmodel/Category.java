package br.com.fiap.tds._tdsq.Library.domainmodel;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {

    private @Setter @Getter  @EqualsAndHashCode.Include Long id;
    private @Setter @Getter String name;

}
