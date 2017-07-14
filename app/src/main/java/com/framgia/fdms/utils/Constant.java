package com.framgia.fdms.utils;

/**
 * Created by Age on 4/3/2017.
 */
public class Constant {
    // TODO: 4/3/2017 develop later
    public static final String END_POINT_URL = "http://stg-dms.framgia.vn/";
    public static final int OUT_OF_INDEX = -1;
    public static final int PER_PAGE = 20;
    public static final int FIRST_PAGE = 1;
    public static final int PICK_IMAGE_REQUEST = 4;
    public static final int ALL_REQUEST_STATUS_ID = -1;
    public static final int ALL_RELATIVE_ID = -1;
    public static final String PERCENT = " %";
    public static final String NOT_SEARCH = "NOT_SEARCH";
    public static final int USING = 1;
    public static final int AVAIABLE = 2;
    public static final int BROKEN = 3;
    public static final int MAX_NOTIFICATION = 99;
    public static final String TITLE_UNKNOWN = "Unknown";
    public static final String ACTION_CLEAR = "Clear";
    public static final String TYPE_DIALOG = "TYPE_DIALOG";
    public static final String FOLDER_NAME_FAMS = "Report FAMS";
    public static final String TYPE_PDF = "application/pdf";
    public static final String TYPE_WORD = "application/msword";
    public static final String TITLE_NOW = "NOW";

    private Constant() {
        // No-op
    }

    public class ApiParram {
        public static final String CATEGORY_ID = "category_id";
        public static final String STATUS_ID = "status_id";
        public static final String PAGE = "page";
        public static final String REQUEST_TYPE = "manager_request";
        public static final String PER_PAGE = "per_page";
        public static final String PRODUCTION_NAME = "production_name";
        public static final String DEVICE_STATUS_ID = "device_status_id";
        public static final String DEVICE_CATEGORY_ID = "device_category_id";
        public static final String BOUGHT_DATE = "bought_date";
        public static final String ORIGINAL_PRICE = "original_price";
        public static final String SERIAL_NUMBER = "serial_number";
        public static final String MODEL_NUMBER = "model_number";
        public static final String DEVICE_CODE = "device_code";
        public static final String PICTURE = "picture";
        public static final String REQUEST_STATUS_ID = "request_status_id";
        public static final String RELATIVE_ID = "relative_id";
        public static final String DEVICE_NAME = "device_name";
        public static final String REQUEST_TITLE = "request[title]";
        public static final String REQUEST_DESCRIPTION = "request[description]";
        public static final String REQUEST_FOR_USER_ID = "request[for_user_id]";
        public static final String REQUEST_ASSIGNEE_ID = "request[assignee_id]";
        public static final String REQUEST_REQUEST_DETAILS = "request[request_details_attributes]";
    }

    public class DeviceStatus {
        public static final String CANCELLED = "cancelled";
        public static final String WAITING_APPROVE = "waiting approve";
        public static final String APPROVED = "approved";
        public static final String WAITING_DONE = "waiting done";
        public static final String DONE = "done";
    }

    public static final class BundleConstant {
        public static final String BUNDLE_CATEGORIES = "BUNDLE_CATEGORIES";
        public static final String BUNDLE_STATUSES = "BUNDLE_STATUSES";
        public static final String BUNDLE_TYPE = "BUNDLE_TYPE";
        public static final String BUNDLE_CATEGORY = "BUNDLE_CATEGORY";
        public static final String BUNDLE_STATUE = "BUNDLE_STATUE";
        public static final String BUNDLE_DEVICE = "BUNDLE_DEVICE";
        public static final String BUNDLE_CONTENT = "BUNDLE_CONTENT";
        public static final String BUNDLE_RESPONE = "BUNDLE_RESPONE";
        public static final String EXTRA_DEVICE_ID = "EXTRA_DEVICE_ID";
        public static final String BUNDLE_TAB = "BUNDLE_TAB";
        public static final String BUNDLE_USER = "USER_BUND";
        public static final String BUNDLE_PRODUCER = "BUNDLE_PRODUCER";
        public static final String BUNDLE_TITLE = "BUNDLE_TITLE";
    }

    public static final class RequestConstant {
        public static final int REQUEST_SELECTION = 1;
        public static final int REQUEST_STATUS = 2;
        public static final int REQUEST_CATEGORY = 3;
        public static final int REQUEST_RELATIVE = 4;
        public static final int REQUEST_ASSIGNER = 5;
        public static final int REQUEST_DETAIL = 6;
        public static final int REQUEST_SCANNER = 7;
        public static final int REQUEST_CREATE_REQUEST = 8;
        public static final int REQUEST_CREATE_ASSIGNMENT = 9;
        public static final int REQUEST_BRANCH = 10;
    }

    public class BundleRequest {
        public static final String BUND_REQUEST = "BUND_REQUEST";
    }

    public static final class Role {
        public static final String STAFF = "staff";
        public static final String DIVISION_MANAGER = "division_manager";
        public static final String BO_MANAGER = "manager";
        public static final String BO_STAFF = "bo_staff";
        public static final String ADMIN = "admin";
    }

    public class RequestAction {
        public static final int CANCEL = 1;
        public static final int WAITING_APPROVE = 2;
        public static final int APPROVED = 3;
        public static final int WAITING_DONE = 4;
        public static final int DONE = 5;
    }
}
