package mofa.wangzhe.reactive.service.impl;

import mofa.wangzhe.reactive.model.JurModel;
import mofa.wangzhe.reactive.service.JurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author LD
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class JurServiceImpl implements JurService {

    @Autowired
    private R2dbcEntityTemplate template;

    @Override
    public Mono<Integer> save(JurModel model) {
        if (!StringUtils.hasText(model.getAccId())) {
            return Mono.error(new Exception("请选择账号"));
        }
        return template.delete(Query.query(Criteria.where("acc_id").is(model.getAccId())), JurModel.class)
                .flatMap(f -> {
                    String menuId = model.getMenuId();
                    menuId = menuId.trim();
                    String[] split = menuId.split(",");
                    for (String s : split) {
                        if (StringUtils.hasText(s)) {
                            JurModel jurModel = new JurModel();
                            jurModel.setUuid(UUID.randomUUID().toString());
                            jurModel.setAccId(model.getAccId());
                            jurModel.setMenuId(s);
                            template.insert(jurModel)
                                    .subscribe();
                        }
                    }
                    return Mono.just(f);
                });
    }

    @Override
    public Mono<JurModel> remove(String uuid) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(uuid)), JurModel.class)
                .flatMap(f -> template.delete(f))
                .switchIfEmpty(Mono.error(new Exception("未查询到数据")));
    }

    @Override
    public Mono<JurModel> update(JurModel model) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(model.getUuid())), JurModel.class)
                .flatMap(f -> {
                    //if (StringUtils.hasText(model.getPassword())) {
                    //    f.setPassword(model.getPassword());
                    //}
                    return template.update(f);
                })
                .switchIfEmpty(Mono.error(new Exception("未查询到数据")));
    }

    @Override
    public Flux<JurModel> list(String accId) {

        if (StringUtils.hasText(accId)) {
            Query query = Query.query(Criteria.where("acc_id").is(accId));
            return template.select(query, JurModel.class);
        } else {
            return Flux.empty();
        }
    }

    @Override
    public Mono<JurModel> one(String uuid) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(uuid)), JurModel.class)
                .switchIfEmpty(Mono.error(new Exception("未查询到数据")));
    }
}
