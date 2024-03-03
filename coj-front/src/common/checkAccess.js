import roleEnum from "@/common/roleEnum.js";

export default function (currentAccess, needAccess) {

  if (needAccess === roleEnum.NOT_LONGIN) {
    return true;
  }

  if (needAccess === roleEnum.USER && currentAccess === roleEnum.NOT_LONGIN) {
    return false;
  }

  if (needAccess === roleEnum.ADMIN && currentAccess !== roleEnum.ADMIN) {
    return false;
  }

  return true;
};