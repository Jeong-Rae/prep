import winston from 'winston';

const { combine, timestamp, printf, colorize } = winston.format;

const baseFormat = printf(
  ({ timestamp, level, message }) => `[${timestamp}] [${level}]: ${message}`,
);

const traceFormat = printf(({ timestamp, level, message, stack }) =>
  stack
    ? `[${timestamp}] [${level}]: ${message}\nStack Trace:\n${stack}`
    : `[${timestamp}] [${level}]: ${message}`,
);

const LOG_FORMAT = {
  info: combine(colorize(), timestamp(), baseFormat),
  warn: combine(colorize(), timestamp(), baseFormat),
  error: combine(colorize(), timestamp(), baseFormat),
  trace: combine(colorize(), timestamp(), traceFormat),
};

const createLogger = (format: winston.Logform.Format) =>
  winston.createLogger({
    level: 'info',
    format,
    transports: [new winston.transports.Console()],
  });

export const logInfo = (message: string) => createLogger(LOG_FORMAT.info).info(message);
export const logWarn = (message: string) => createLogger(LOG_FORMAT.warn).warn(message);
export const logError = (error: Error | string) =>
  error instanceof Error
    ? createLogger(LOG_FORMAT.error).error(error.message)
    : createLogger(LOG_FORMAT.error).error(error);
export const logTrace = (error: Error | string) =>
  error instanceof Error
    ? createLogger(LOG_FORMAT.trace).error(error.message, { stack: error.stack })
    : createLogger(LOG_FORMAT.trace).error(error);

export const logger = {
  info: logInfo,
  warn: logWarn,
  error: logError,
  trace: logTrace,
};
