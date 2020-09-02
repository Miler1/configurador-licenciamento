"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _api = _interopRequireDefault(require("./api.service"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var _default = {
  salvar: function salvar(taxaAdministrativa) {
    return regeneratorRuntime.async(function salvar$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            return _context.abrupt("return", _api["default"].post('/taxaAdministrativa/salvar', taxaAdministrativa));

          case 1:
          case "end":
            return _context.stop();
        }
      }
    });
  }
};
exports["default"] = _default;