package com.windfally.uploader.events.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.windfally.uploader.events.domain.Event;
import com.windfally.uploader.events.dto.EventSearchCondition;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.windfally.uploader.events.domain.QAge.age1;
import static com.windfally.uploader.events.domain.QDetail.detail;
import static com.windfally.uploader.events.domain.QEvent.event;
import static com.windfally.uploader.events.domain.QShowDate.showDate1;
import static com.windfally.uploader.events.domain.QTag.tag;


@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Event> search(EventSearchCondition eventSearchCondition) {
        LocalDate localDate = null;
        if (eventSearchCondition.getShowDate() != null) {
            localDate = LocalDate.parse(eventSearchCondition.getShowDate(), DateTimeFormatter.ISO_DATE);
        }
        return queryFactory
                .selectFrom(event)
                .leftJoin(event.ages, age1)
                .fetchJoin()
                .leftJoin(event.tags, tag)
                .fetchJoin()
                .leftJoin(event.showDates, showDate1)
                .fetchJoin()
                .leftJoin(event.detail, detail)
                .fetchJoin()
                .where(ageEq(eventSearchCondition.getAge()), tagContains(eventSearchCondition.getTag()),
                        cityEq(eventSearchCondition.getCity()), districtEq(eventSearchCondition.getDistrict()),
                        showDateEq(localDate), typeEq(eventSearchCondition.getType()), titleContains(eventSearchCondition.getTitle()))
                .distinct()        // distinct로 중복제거하기 보단 hibernate.initialize 변경이 불필요한 데이터 전송량 제거
                .fetch();
    }

    private Predicate titleContains(String title) {
        return title != null ? event.title.contains(title) : null;
    }

    private Predicate typeEq(String type) {
        return type != null ? event.type.eq(type) : null;
    }

    private Predicate showDateEq(LocalDate showDate) {
        return showDate != null ? event.showDates.any().showDate.eq(showDate) : null;
    }

    private Predicate districtEq(String district) {
        return district != null ? event.address.district.eq(district) : null;
    }

    private Predicate tagContains(String tag) {
        return tag != null ? event.tags.any().name.contains(tag) : null;
    }

    private Predicate cityEq(String city) {
        return city != null ? event.address.city.eq(city) : null;
    }

    private Predicate ageEq(Integer age) {
        return age != null ? event.ages.any().age.eq(age) : null;
    }
}
