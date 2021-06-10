package mofa.wangzhe.reactive.service.impl;

import mofa.wangzhe.reactive.model.AccountModel;
import mofa.wangzhe.reactive.service.AccountService;
import mofa.wangzhe.reactive.util.md5.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.UUID;

/**
 * 参考https://zhuanlan.zhihu.com/p/366456122
 *
 * @author LD
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private R2dbcEntityTemplate template;

    @Override
    public Mono<AccountModel> save(AccountModel model) {
        model.setUuid(UUID.randomUUID().toString());
        model.setState(1);
        String md5Str = Md5Util.getMd5Str(model.getPassword());
        model.setPassword(md5Str);
        return template.selectOne(Query.query(Criteria.where("account").is(model.getAccount())), AccountModel.class)
                .flatMap(f -> Mono.error(new Exception("账号重复")))
                .switchIfEmpty(template.insert(model)
                        .switchIfEmpty(Mono.error(new Exception("未查询到数据"))))
                .cast(AccountModel.class);

    }

    @Override
    public Mono<AccountModel> remove(String uuid) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(uuid)), AccountModel.class)
                .flatMap(f -> template.delete(f))
                .switchIfEmpty(Mono.error(new Exception("未查询到数据")));
    }

    @Override
    public Mono<AccountModel> update(AccountModel model) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(model.getUuid())), AccountModel.class)
                .flatMap(f -> {
                    if (StringUtils.hasText(model.getPassword())) {
                        f.setPassword(model.getPassword());
                    }
                    if (model.getState() != 0) {
                        f.setState(model.getState());
                    }
                    return template.update(f);
                })
                .switchIfEmpty(Mono.error(new Exception("未查询到数据")));
    }

    @Override
    public Mono<Tuple2<Long, List<AccountModel>>> page(int pageSize, int pageNum, String search) {
        Query query;
        if (StringUtils.hasText(search)) {
            query = Query.query(Criteria.where("account").like("%" + search + "%"));
        } else {
            query = Query.query(CriteriaDefinition.empty());
        }
        query = query.with(PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "account")));

        Mono<List<AccountModel>> listMono = template.select(query, AccountModel.class)
                .map(m -> {
                    m.setPassword(null);
                    return m;
                })
                .collectList();
        Mono<Long> count = template.select(query, AccountModel.class)
                .count();
        return Mono.zip(count, listMono);
    }

    @Override
    public Mono<AccountModel> one(String uuid) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(uuid)), AccountModel.class)
                .switchIfEmpty(Mono.error(new Exception("未查询到数据")));
    }

    @Override
    public Mono<AccountModel> oneByAccount(String account) {
        return template.selectOne(Query.query(Criteria.where("account").is(account)), AccountModel.class)
                .switchIfEmpty(Mono.error(new Exception("未查询到数据")));
    }
}
