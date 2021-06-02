package mofa.wangzhe.reactive.service.impl;

import mofa.wangzhe.reactive.model.AccountModel;
import mofa.wangzhe.reactive.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        return template.insert(model)
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }

    @Override
    public Mono<AccountModel> remove(AccountModel model) {
        return template.delete(model);
    }

    @Override
    public Mono<AccountModel> update(AccountModel model) {
        return template.update(model)
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }

    @Override
    public Flux<AccountModel> page(int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "acc"));
        Query empty = Query.query(Criteria.empty())
                .sort(Sort.by(Sort.Direction.ASC, "acc"))
                .with(pageable);
        return template.select(AccountModel.class)
                .matching(empty)
                .all();
    }

}
