package mofa.wangzhe.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author LD
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountModel {

    private String uuid;

    private String account;

    private String password;
}
