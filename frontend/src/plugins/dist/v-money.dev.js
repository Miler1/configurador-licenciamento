"use strict";

var _vue = _interopRequireDefault(require("vue"));

var _vMoney = _interopRequireDefault(require("v-money"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

// register directive v-money and component <money>
_vue["default"].use(_vMoney["default"], {
  precision: 4
});