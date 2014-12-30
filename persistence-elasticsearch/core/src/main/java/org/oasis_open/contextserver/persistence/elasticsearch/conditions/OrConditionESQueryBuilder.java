package org.oasis_open.contextserver.persistence.elasticsearch.conditions;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.oasis_open.contextserver.api.conditions.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by loom on 06.08.14.
 */
public class OrConditionESQueryBuilder implements ConditionESQueryBuilder {

    public OrConditionESQueryBuilder() {
    }

    public FilterBuilder buildFilter(Condition condition, Map<String, Object> context, ConditionESQueryBuilderDispatcher dispatcher) {
        List<Condition> conditions = (List<Condition>) condition.getParameterValues().get("subConditions");

        List<FilterBuilder> l = new ArrayList<FilterBuilder>();
        for (Object sub : conditions) {
            l.add(dispatcher.buildFilter((Condition) sub, context));
        }

        if (l.size() == 1) {
            return l.get(0);
        }

        return FilterBuilders.orFilter(l.toArray(new FilterBuilder[l.size()]));
    }
}
