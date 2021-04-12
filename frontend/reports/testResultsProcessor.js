module.exports = function process(report) {
  report.testResults.forEach((test_file) => {
    test_file.testResults.forEach((test_result) => {
      const messages = [];
      test_result.failureMessages.forEach((m) => {
        let message = m;
        message = JSON.stringify(message);
        message = message.replace(/['"]+/g, "");
        message = message.replace(/\\n/g, "\n");
        message = message.replace(/\\u001b\[[0-9]+m/gm, "");
        messages.push(message);
      });
      // eslint-disable-next-line no-param-reassign
      test_result.failureMessages = messages;
    });
  });
  return report;
};
