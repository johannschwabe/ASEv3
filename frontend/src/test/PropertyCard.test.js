import {mount} from "@vue/test-utils";
import PropertyCard from "../components/property/PropertyCard.vue";

/**
 * Test if the component PropertyCard Mounts
 */
describe("propertyCardMounts", () => {
  test("1.1 mounts", () => {
    const card = mount(PropertyCard, {
      propsData: {
        property: {
          reviews_per_month: 2,
          availability_365: 10,
        },
      },
    });
    expect(card.isVueInstance()).toBeTruthy();
  });
});
