package heroes.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum District {
    JNO("종로구"),
    JGU("중구"),
    YSN("용산구"),
    SDG("성동구"),
    GWJ("광진구"),
    DDM("동대문구"),
    JNG("중랑구"),
    SBK("성북구"),
    GBK("강북구"),
    DBG("도봉구"),
    NWN("노원구"),
    EPG("은평구"),
    SDM("서대문구"),
    MGP("마포구"),
    YGC("양천구"),
    GSG("강서구"),
    GRG("구로구"),
    GCG("금천구"),
    YDP("영등포구"),
    DJK("동작구"),
    GWK("관악구"),
    SCG("서초구"),
    GNM("강남구"),
    SGP("송파구"),
    GDG("강동구");

    private String value;
}
