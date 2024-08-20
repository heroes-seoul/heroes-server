package heroes.domain.sublevel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubLevel {
    LV0101(1, "예스 키즈존 등록하기"),
    LV0201(2, "안전 기준 준수하기"),
    LV0202(2, "기본 안전장치 설치하기"),
    LV0301(3, "안전성 기준 충족하기"),
    LV0302(3, "어린이 식기 구비하기"),
    LV0401(4, "어린이 물티슈 제공하기"),
    LV0402(4, "어린이용 의자 구비하기"),
    LV0501(5, "아기를 위한 환경 마련하기"),
    LV0502(5, "수유실 마련하기"),
    LV0601(6, "부모 휴식 공간 마련하기"),
    LV0602(6, "어린이 휴식 공간 마련하기"),
    LV0701(7, "아이용 그리기 미술도구 구비하기"),
    LV0702(7, "어린이 놀이방 만들기"),
    LV0801(8, "유모차 비치공간 마련하기"),
    LV0802(8, "가족 화장실 마련하기"),
    LV0901(9, "양육환경 조성 직원교육 의무 실시하기"),
    LV1001(10, "지역단체 협력 프로그램 조성하기"),
    LV1002(10, "부모/아이 협력 프로그램 개설하기");

    private int level;
    private String value;
}
