
package mofa.wangzhe.reactive.service.impl;

import mofa.wangzhe.reactive.model.TttModel;
import mofa.wangzhe.reactive.service.TttService;
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
 *
 *
 * @author LD
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class TttServiceImpl implements TttService {

    @Autowired
    private R2dbcEntityTemplate template;

    @Override
    public Mono<TttModel> save(TttModel model) {
        model.setUuid(UUID.randomUUID().toString());
        return template.insert(model)
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }

    @Override
    public Mono<TttModel> remove(String uuid) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(uuid)), TttModel.class)
                .flatMap(f -> template.delete(f))
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }

    @Override
    public Mono<TttModel> update(TttModel model) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(model.getUuid())), TttModel.class)
                .flatMap(f -> {
                    //if (StringUtils.hasText(model.getPassword())) {
                    //    f.setPassword(model.getPassword());
                    //}
                    return template.update(f);
                })
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }

    @Override
    public Mono<Tuple2<Long, List<TttModel>>> page(int pageSize, int pageNum, String search) {

        Query query;
        if (StringUtils.hasText(search)) {
            query = Query.query(Criteria.where("uuid").like("%"+search+"%"));
        } else {
            query = Query.query(CriteriaDefinition.empty());
        }
        query = query.with(PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "uuid")));

        Mono<List<TttModel>> listMono = template.select(query, TttModel.class)
                .collectList();
        Mono<Long> count = template.select(query, TttModel.class)
                .count();
        return Mono.zip(count,listMono);
    }

    @Override
    public Mono<TttModel> one(String uuid) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(uuid)), TttModel.class)
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }
}
