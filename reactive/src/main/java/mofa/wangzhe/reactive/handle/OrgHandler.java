package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.OrgModel;
import mofa.wangzhe.reactive.service.OrgService;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author LD
 */

@Slf4j
@Component
public class OrgHandler {

    private final OrgService service;

    @Autowired
    public OrgHandler(OrgService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMINS')")
    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(OrgModel.class)
                .flatMap(f -> this.service.save(f)
                        .flatMap(f2 -> ResultUtil2.ok(null)))
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    @PreAuthorize("hasRole('ADMINS')")
    public Mono<ServerResponse> remove(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        OrgModel model = new OrgModel();
        model.setUuid(uuid);
        return this.service.remove(model)
                .flatMap(f2 -> ResultUtil2.ok(null));
    }

    @PreAuthorize("hasRole('ADMINS')")
    public Mono<ServerResponse> update(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return request.bodyToMono(OrgModel.class)
                .flatMap(f -> {
                    f.setUuid(uuid);
                    return this.service.update(f)
                            .flatMap(f2 -> ResultUtil2.ok(null));
                })
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    /**
     * menu list
     *
     * @param request ServerRequest
     * @return Mono<ServerResponse>
     */
    public Mono<ServerResponse> list(ServerRequest request) {
        String pid = request.pathVariable("pid");
        return this.service.findAll(pid)
                .collectList()
                .flatMap(f -> Mono.just(getTreeList(f)))
                .flatMap(ResultUtil2::ok);
    }

    private static List<OrgModel> getTreeList(List<OrgModel> entityList) {
        List<OrgModel> resultList = new ArrayList<>();
        //获取顶层元素集合
        String pid;
        for (OrgModel entity : entityList) {
            pid = entity.getPid();
            //顶层元素的pid==null或者为0
            if (Objects.equals("0", pid)) {
                resultList.add(entity);
            }
        }
        //获取每个顶层元素的子数据集合
        for (OrgModel entity : resultList) {
            entity.setChildren(getSubList(entity.getUuid(), entityList));
        }
        return resultList;
    }

    private static List<OrgModel> getSubList(String id, List<OrgModel> entityList) {
        List<OrgModel> childList = new ArrayList<>();
        String pid;
        //子集的直接子对象
        for (OrgModel entity : entityList) {
            pid = entity.getPid();
            if (id.equals(pid)) {
                childList.add(entity);
            }
        }
        //子集的间接子对象
        for (OrgModel entity : childList) {
            entity.setChildren(getSubList(entity.getUuid(), entityList));
        }
        //递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

}
