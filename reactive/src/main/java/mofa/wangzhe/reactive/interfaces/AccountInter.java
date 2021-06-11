package mofa.wangzhe.reactive.interfaces;

/**
 * 此类和MyAbstractR2dbcConfiguration.java冲突，要使用此类则删除掉MyAbstractR2dbcConfiguration.java
 *
 * @author LD
 */

//@Repository
public interface AccountInter {
}
//        extends R2dbcRepository<AccountModel, String> {
//
//    @Query(
//            "select acc.*,org.uuid as orgModel.uuid from account_table as acc " +
//                    " left join org_table org on org.uuid = acc.org " +
//                    " where acc.uuid = :uuid")
//    Mono<AccountModel> ones(@Param("uuid") String uuid);
//}
